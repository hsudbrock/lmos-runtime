/*
 * SPDX-FileCopyrightText: 2025 Deutsche Telekom AG and others
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package org.eclipse.lmos.runtime.outbound

import ai.ancf.lmos.wot.Wot
import org.eclipse.lmos.runtime.core.LmosRuntimeConfig
import org.eclipse.lmos.runtime.core.model.AssistantMessage
import org.eclipse.lmos.runtime.core.model.Conversation
import org.eclipse.lmos.runtime.core.service.outbound.AgentClientService
import org.slf4j.LoggerFactory

class ArcAgentClientService(private val wot: Wot,
    private val lmosRuntimeConfig: LmosRuntimeConfig) : AgentClientService {
    private val log = LoggerFactory.getLogger(ArcAgentClientService::class.java)

    override suspend fun askAgent(
        conversation: Conversation,
        conversationId: String,
        turnId: String,
        agentName: String,
        wotThingDescriptionId: String,
        subset: String?,
    ): AssistantMessage {
        val thingDescriptionUrl = "${lmosRuntimeConfig.agentRegistry.baseUrl}/apis/v1/things/$wotThingDescriptionId"
        val agent = WotConversationalAgent.create(thingDescriptionUrl)
        val chatResponse = agent.chat(conversation.inputContext.messages.first().content)
        return AssistantMessage(chatResponse)
    }
}
