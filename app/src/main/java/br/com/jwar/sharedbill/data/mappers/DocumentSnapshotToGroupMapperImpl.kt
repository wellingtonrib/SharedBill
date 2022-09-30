package br.com.jwar.sharedbill.data.mappers

import br.com.jwar.sharedbill.domain.model.Group
import br.com.jwar.sharedbill.domain.model.User
import com.google.firebase.firestore.DocumentSnapshot
import javax.inject.Singleton

@Singleton
class DocumentSnapshotToGroupMapperImpl : DocumentSnapshotToGroupMapper {
    override fun mapFrom(from: DocumentSnapshot) = with(from) {
        Group(
            id = getString("id").orEmpty(),
            title = getString("title").orEmpty(),
            owner = get("owner", User::class.java) ?: User(),
            members = emptyList(),
            membersIds = emptyList(),
            payments = emptyList(),
            balance = emptyMap()
        )
    }
}