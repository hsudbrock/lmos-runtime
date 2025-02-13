package org.eclipse.lmos.runtime.service.inbound.agent

import ai.ancf.lmos.wot.protocol.LMOSContext
import ai.ancf.lmos.wot.protocol.LMOSThingType
import ai.ancf.lmos.wot.reflection.annotations.Context
import ai.ancf.lmos.wot.reflection.annotations.Property
import ai.ancf.lmos.wot.reflection.annotations.Thing
import ai.ancf.lmos.wot.reflection.annotations.VersionInfo
import kotlinx.coroutines.flow.MutableStateFlow
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
class RuntimeAgent {
    @Property(title = "current mood", readOnly = true)
    val observableProperty: MutableStateFlow<String> = MutableStateFlow("bad")
}
