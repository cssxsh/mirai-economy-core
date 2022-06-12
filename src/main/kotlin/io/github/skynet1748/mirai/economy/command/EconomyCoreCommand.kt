package io.github.skynet1748.mirai.economy.command

import net.mamoe.mirai.console.command.Command

public sealed interface EconomyCoreCommand : Command {
    public companion object : Collection<EconomyCoreCommand> {
        private val commands by lazy {
            EconomyCoreCommand::class.sealedSubclasses.mapNotNull { kClass -> kClass.objectInstance }
        }

        override val size: Int get() = commands.size

        override fun contains(element: EconomyCoreCommand): Boolean = commands.contains(element)

        override fun containsAll(elements: Collection<EconomyCoreCommand>): Boolean = commands.containsAll(elements)

        override fun isEmpty(): Boolean = commands.isEmpty()

        override fun iterator(): Iterator<EconomyCoreCommand> = commands.iterator()

        public operator fun get(name: String): EconomyCoreCommand = commands.first { it.primaryName.equals(name, true) }
    }
}