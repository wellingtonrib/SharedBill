package br.com.jwar.sharedbill.data.mappers

import br.com.jwar.sharedbill.domain.model.Group
import com.google.firebase.firestore.DocumentSnapshot

interface DocumentSnapshotToGroupMapper {
    fun mapFrom(from: DocumentSnapshot): Group
}