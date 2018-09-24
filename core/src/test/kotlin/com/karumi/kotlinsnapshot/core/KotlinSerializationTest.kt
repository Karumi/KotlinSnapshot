package com.karumi.kotlinsnapshot.core

import com.karumi.kotlinsnapshot.KotlinSnapshot
import com.karumi.kotlinsnapshot.matchWithSnapshot
import org.junit.Test
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.Locale
import java.util.TimeZone

class KotlinSerializationTest {

    private val snap = KotlinSnapshot(KotlinSerialization())
    private val customSnap = KotlinSnapshot(CustomKotlinSerialization())

    @Test
    fun `should serialize a primitive value`() {
        snap.matchWithSnapshot(1, "should serialize a int")
        snap.matchWithSnapshot(6.643323, "should serialize a double")
        snap.matchWithSnapshot(2342342342L, "should serialize a long")
        snap.matchWithSnapshot(6.toShort(), "should serialize a short")
        snap.matchWithSnapshot(0xF.toByte(), "should serialize a byte")
    }

    @Test
    fun `should serialize a String`() {
        snap.matchWithSnapshot("Simple string")
    }

    @Test
    fun `should serialize an enum`() {
        snap.matchWithSnapshot(Primitives.DOUBLE)
    }

    @Test
    fun `should serialize a enum list`() {
        val primitives = listOf(
            Primitives.INT,
            Primitives.DOUBLE,
            Primitives.LONG
        )
        snap.matchWithSnapshot(primitives)
    }

    @Test
    fun `should serialize an Array`() {
        val users = arrayOf(
            User(1, "gabriel"),
            User(2, "andres"),
            User(3, "miguel")
        )
        snap.matchWithSnapshot(users)
    }

    @Test
    fun `should serialize an array inside to another array`() {
        val users = arrayOf(
            User(1, "gabriel"),
            User(2, "andres"),
            User(3, "miguel")
        )
        snap.matchWithSnapshot(arrayOf(users))
    }

    @Test
    fun `should serialize a list inside to another list`() {
        val users = listOf(
            User(1, "gabriel"),
            User(2, "andres"),
            User(3, "miguel")
        )
        snap.matchWithSnapshot(listOf(users))
    }

    @Test
    fun `should serialize a simple map`() {
        val map = mapOf(
            1 to User(1, "gabriel"),
            2 to User(2, "andres"),
            3 to User(3, "miguel")
        )
        snap.matchWithSnapshot(map)
    }

    @Test
    fun `should serialize a complex map`() {
        val mobileTeam = arrayOf(
            Developer("gabriel", 3),
            Developer("andres", 3),
            Developer("miguel", 3)
        )
        val developerByTeam = mapOf(
            "product" to listOf(User(1, "gabriel")),
            "sales" to arrayOf(User(1, "ramon")),
            "mobile" to mobileTeam
        )
        snap.matchWithSnapshot(developerByTeam)
        snap.matchWithSnapshot(developerByTeam.toSortedMap())
    }

    @Test
    fun `should serialize a kotlin Pair object`() {
        val pair = 1 to User(1, "gabriel")
        snap.matchWithSnapshot(pair)
    }

    @Test
    fun `should serialize a sealed class member`() {
        val badRequest = NetworkError.BadRequest("Body is required.")
        snap.matchWithSnapshot(badRequest)
    }

    @Test
    fun `should serialize an inner object of a sealed class`() {
        val request = Request(false, NetworkError.InternalServerError("SQLException"))
        snap.matchWithSnapshot(request)
    }

    @Test
    fun `should serialize an kotlin object of a sealed class`() {
        val notFound = NetworkError.NotFound
        snap.matchWithSnapshot(notFound)
    }

    @Test
    fun `should serialize a class with a kotlin object of a sealed class`() {
        val request = Request(false, NetworkError.NotFound)
        snap.matchWithSnapshot(request)
    }

    @Test
    fun `should serialize a kotlin object`() {
        val kotlinObject = KotlinObject
        snap.matchWithSnapshot(kotlinObject)
    }

    @Test
    fun `should serialize a kotlin Set`() {
        val mobileTeam = setOf(
            Developer("gabriel", 3),
            Developer("andres", 3),
            Developer("miguel", 3)
        )
        snap.matchWithSnapshot(mobileTeam)
    }

    @Test
    fun `should serialize a LinkedHashMap`() {
        val developerByTeamMap = mapOf(
            "product" to listOf(User(1, "gabriel")),
            "sales" to arrayOf(User(1, "ramon")),
            "mobile" to Developer("miguel", 3)
        )
        val developerByTeamLinked = linkedMapOf(
            "product" to listOf(User(1, "gabriel")),
            "sales" to arrayOf(User(1, "ramon")),
            "mobile" to Developer("miguel", 3)
        )
        val developerByTeamSorted = sortedMapOf(
            "product" to listOf(User(1, "gabriel")),
            "sales" to arrayOf(User(1, "ramon")),
            "mobile" to Developer("miguel", 3)
        )
        snap.matchWithSnapshot(developerByTeamMap)
        snap.matchWithSnapshot(developerByTeamLinked)
        snap.matchWithSnapshot(developerByTeamSorted)
    }

    @Test
    fun `should serialize a null object when is a member field`() {
        val user = UserWithOptionalName(1, null)
        snap.matchWithSnapshot(user)
    }

    @Test
    fun `should serialize Date`() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.US)
        dateFormat.timeZone = TimeZone.getTimeZone("UTC")
        val date = dateFormat.parse("2007-12-03")
        date.matchWithSnapshot()
    }

    @Test
    fun `should serialize Date object with time and timezone`() {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd:HH:mm:ss.SSS", Locale.US)
        dateFormat.timeZone = TimeZone.getTimeZone("GMT")
        val date = dateFormat.parse("2007-12-03:18:46:19.333")
        date.matchWithSnapshot()
    }

    @Test
    fun `should serialize LocalDate object`() {
        val localDate = LocalDate.parse("2007-12-03")
        localDate.matchWithSnapshot()
    }

    @Test
    fun `should serialize LocalDateTime object`() {
        val dateTime = LocalDateTime.parse("2007-12-03T10:15:30")
        dateTime.matchWithSnapshot()
    }

    @Test
    fun `should let the user customize the serializer behavior`() {
        val localDate = LocalDate.parse("2007-12-03")
        customSnap.matchWithSnapshot(localDate)
    }

    enum class Primitives { INT, DOUBLE, LONG }

    class User(val id: Int, val name: String)
    class UserWithOptionalName(val id: Int, val name: String?)

    data class Developer(val name: String, val githubStars: Int)

    class Request(val isSuccess: Boolean, val networkError: NetworkError)

    object KotlinObject

    sealed class NetworkError {
        object NotFound : NetworkError()
        data class InternalServerError(val message: String) : NetworkError()
        class BadRequest(val error: String)
    }

    class CustomKotlinSerialization : SerializationModule {

        private val kotlinSerialization = KotlinSerialization()

        override fun serialize(value: Any?): String = when {
            value is LocalDate -> "custom serialization configured"
            else -> kotlinSerialization.serialize(value)
        }
    }
}
