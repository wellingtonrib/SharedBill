package br.com.jwar.sharedbill.groups.domain.model

import br.com.jwar.sharedbill.account.domain.model.User

data class Group(
    val id: String = "",
    val title: String = "",
    val owner: User = User(),
    val members: List<User> = emptyList(),
    val firebaseMembersIds: List<String> = emptyList(),
    val payments: List<Payment> = emptyList(),
    val balance: Map<String, String> = emptyMap()
) {
    fun findMemberById(id: String) =
        members.firstOrNull { it.id == id }

    fun findCurrentUser() =
        members.firstOrNull { it.isCurrentUser }
}
