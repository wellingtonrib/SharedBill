package br.com.jwar.sharedbill.account.data.mappers

import com.google.firebase.auth.FirebaseUser
import io.mockk.every
import io.mockk.mockk
import org.junit.Test
import java.util.UUID
import kotlin.test.assertEquals

internal class FirebaseUserToUserMapperImplTest {

    private val firebaseUserToUserMapper = FirebaseUserToUserMapperImpl()

    @Test
    fun `mapFrom should map and return a domain user`() {
        val firebaseUser = mockk<FirebaseUser> {
            every { uid } returns UUID.randomUUID().toString()
            every { displayName } returns "User name"
            every { email } returns "user@email.com"
            every { photoUrl } returns null
        }

        val result = firebaseUserToUserMapper.mapFrom(firebaseUser)

        assertEquals(firebaseUser.uid, result.firebaseUserId)
        assertEquals(firebaseUser.displayName, result.name)
        assertEquals(firebaseUser.email, result.email)
        assertEquals(firebaseUser.photoUrl?.toString(), result.photoUrl)
    }
}