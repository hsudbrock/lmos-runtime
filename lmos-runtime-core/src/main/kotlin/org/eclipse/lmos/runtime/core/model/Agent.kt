/*
 * SPDX-FileCopyrightText: 2025 Deutsche Telekom AG and others
 *
 * SPDX-License-Identifier: Apache-2.0
 */

package org.eclipse.lmos.runtime.core.model

data class Agent(
    val name: String,
    val version: String,
    val description: String,
    val capabilities: List<AgentCapability>,
    val wotThingDescriptionId: String,
)

data class Address(
    val protocol: String = "http",
    val uri: String,
)

data class AgentCapability(
    val name: String,
    val version: String,
    val description: String,
    val subset: String? = "",
)

class AgentBuilder {
    private var name: String = ""
    private var version: String = ""
    private var description: String = ""
    private var capabilities: MutableList<AgentCapability> = mutableListOf()
    private var wotThingDescriptionId: String = ""

    fun name(name: String) = apply { this.name = name }

    fun version(version: String) = apply { this.version = version }

    fun description(description: String) = apply { this.description = description }

    fun wotThingDescriptionId(wotThingDescriptionId: String) = apply { this.wotThingDescriptionId = wotThingDescriptionId }

    fun addCapability(capability: AgentCapability) = apply { this.capabilities.add(capability) }

    fun capabilities(capabilities: List<AgentCapability>) = apply { this.capabilities.addAll(capabilities) }

    fun build(): Agent {
        return Agent(
            name = name,
            version = version,
            description = description,
            capabilities = capabilities.toList(),
            wotThingDescriptionId = wotThingDescriptionId,
        )
    }
}
