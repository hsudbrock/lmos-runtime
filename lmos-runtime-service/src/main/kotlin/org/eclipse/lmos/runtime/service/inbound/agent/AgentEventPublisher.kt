package org.eclipse.lmos.runtime.service.inbound.agent

import kotlinx.coroutines.flow.MutableSharedFlow
import org.springframework.stereotype.Component

@Component
class AgentEventPublisher {

    val messageFlow = MutableSharedFlow<AgentEvent>(replay = 1)

}
