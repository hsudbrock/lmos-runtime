package org.eclipse.lmos.runtime.service.inbound.agent

import ai.ancf.lmos.wot.protocol.LMOSContext
import ai.ancf.lmos.wot.protocol.LMOSThingType
import ai.ancf.lmos.wot.reflection.annotations.*
import kotlinx.coroutines.flow.MutableStateFlow
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
class RuntimeAgent(private val conversationHandler: ConversationHandler) {
    @Property(title = "current mood", readOnly = true)
    val observableProperty: MutableStateFlow<String> = MutableStateFlow("bad")


    @Action(title = "chat", description = "chat with the agents exposed by the runtime")
    suspend fun chat(chatInput: ChatInput): AssistantMessage {
        val assistantMessage = conversationHandler.handleConversation(chatInput.conversation, chatInput.conversationId, chatInput.tenantId, chatInput.turnId)
        return assistantMessage
    }

}

data class ChatInput(
    val conversation: Conversation,
    val turnId: String,
    val conversationId: String,
    val tenantId: String
)
