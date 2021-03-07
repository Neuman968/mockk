package io.mockk.gh

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import net.bytebuddy.asm.MemberSubstitution.Substitution.Chain.with
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class Obj(val value: Int)

data class DataObj(val value: Int)

class Ext {
    fun Obj.extensionFunc() = value + 5

    fun DataObj.extensionFunc() = value + 4
}

class Issue504Test {

    @Test
    fun shouldVerifyMockExtensionNonDataClass() {
        with(mockk<Ext>()) {
            every {
                Obj(5).extensionFunc()
            } returns 11

            assertEquals(11, Obj(5).extensionFunc())

            verify {
                Obj(5).extensionFunc()
            }
        }
    }

    @Test
    fun shouldVerifyMockExtensionDataClass() {
        with(mockk<Ext>()) {
            every {
                DataObj(5).extensionFunc()
            } returns 11

            assertEquals(11, DataObj(5).extensionFunc())

            verify {
                DataObj(5).extensionFunc()
            }
        }
    }
}