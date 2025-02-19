package org.eclipse.lmos.runtime.service.inbound.agent

import ai.ancf.lmos.wot.protocol.LMOSContext
import ai.ancf.lmos.wot.protocol.LMOSThingType
import ai.ancf.lmos.wot.reflection.annotations.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import org.eclipse.lmos.runtime.core.inbound.ConversationHandler
import org.eclipse.lmos.runtime.core.model.AssistantMessage
import org.eclipse.lmos.runtime.core.model.Conversation
import org.springframework.stereotype.Component

@Thing(
    id = "runtime-agent",
    title = "Runtime Agent",
    description = "The runtime agent",
    type = LMOSThingType.AGENT,
)
@Context(
    prefix = LMOSContext.prefix,
    url = LMOSContext.url,
)
@VersionInfo(instance = "1.0.0")
@Component
class RuntimeAgent(private val conversationHandler: ConversationHandler, private val agentEventPublisher: AgentEventPublisher) {


    @Action(title = "chat", description = "chat with the agents exposed by the runtime")
    suspend fun chat(chatInput: ChatInput): AssistantMessage {
        agentEventPublisher.messageFlow.emit(
            AgentEvent(
                type = "LLMFinishedEvent",
                payload = "{\"result\":{\"value\":{\"content\":\"I'm sorry, I cannot help with that issue.\",\"turnId\":null,\"sensitive\":false,\"anonymized\":false,\"binaryData\":[],\"format\":\"TEXT\",\"userTranscript\":null}},\"messages\":[{\"content\":\"# Goal \\nYou are a helpful assistant that can provide information and answer customer questions.\\nYou answer in a helpful and professional manner.  \\n     \\n### Instructions \\n   \\n - Only answer the customer question in a concise and short way.\\n - Only provide information the user has explicitly asked for.\\n - Use the \\\"Knowledge\\\" section to answer customers queries.\\n - If the customer's question is on a topic not described in the \\\"Knowledge\\\" section nor llm functions, reply that you cannot help with that issue.\\n\\n### Knowledge\\n  **Customer would like to know about Arc.**\\n  - Read the content from https://eclipse.dev/lmos/arc/ and provide the answer.\\n\",\"turnId\":null,\"sensitive\":false,\"anonymized\":false,\"binaryData\":[],\"format\":\"TEXT\"},{\"content\":\"test\",\"turnId\":null,\"sensitive\":false,\"anonymized\":false,\"binaryData\":[],\"format\":\"TEXT\"},{\"content\":\"I'm sorry, I cannot help with that issue.\",\"turnId\":null,\"sensitive\":false,\"anonymized\":false,\"binaryData\":[],\"format\":\"TEXT\",\"userTranscript\":null},{\"content\":\"test\",\"turnId\":null,\"sensitive\":false,\"anonymized\":false,\"binaryData\":[],\"format\":\"TEXT\"}],\"functions\":[],\"model\":\"GPT35T-1106\",\"totalTokens\":178,\"promptTokens\":167,\"completionTokens\":11,\"functionCallCount\":0,\"duration\":0.565597417,\"settings\":null,\"context\":{\"turnId\":\"-1\",\"agent\":\"assistant-agent\",\"agentUrl\":\"http://localhost:8500\",\"conversationId\":\"${chatInput.conversationId}\"},\"timestamp\":1739888465.415612000}",
                // payload = "some payload",
                conversationId = chatInput.conversationId,
                turnId = chatInput.turnId,
            )
        )
        val assistantMessage = conversationHandler.handleConversation(
            chatInput.conversation,
            chatInput.conversationId,
            chatInput.tenantId,
            chatInput.turnId
        )

        return assistantMessage
    }

    @Event(description = "Agent events (e.g., when tool was used)")
    fun event(): Flow<AgentEvent> {
        return agentEventPublisher.messageFlow
    }

}

data class AgentEvent(
    val type: String,
    val payload: String,
    val conversationId: String?,
    val turnId: String?,
)

data class ChatInput(
    val conversation: Conversation,
    val turnId: String,
    val conversationId: String,
    val tenantId: String
)
