package br.com.jwar.sharedbill.testing.fakes

import br.com.jwar.sharedbill.account.domain.model.User
import br.com.jwar.sharedbill.groups.domain.model.Group
import java.util.UUID

object FakeFactory {

    fun makeUser(
        id: String = UUID.randomUUID().toString(),
        firebaseUserId: String = UUID.randomUUID().toString(),
        name: String = "User $id",
        email: String = "user$id@domain.com",
        photoUrl: String = "https://picsum.photos/200"
    ) = User(
        id = id,
        firebaseUserId = firebaseUserId,
        name = name,
        email = email,
        photoUrl = photoUrl
    )

    fun makeGroup(
        id: String = UUID.randomUUID().toString(),
        title: String = "Group $id",
        owner: User = makeUser(),
    ) = Group(
        id = id,
        title = title,
        owner = owner,
        members = listOf(owner.copy(isCurrentUser = true)),
        firebaseMembersIds = listOf(owner.firebaseUserId)
    )
}
