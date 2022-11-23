import org.junit.Before
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test

internal class WallServiceTest {

    @Before
    fun clearBeforeTest() {
        WallService.clear()
    }

    @Test
    fun add() {
        val post1 = Post(1, 23)
        val result = WallService.add(post1)
        assertEquals(1, result.id)
    }

    @Test
    fun update() {
        val post1 = Post(1, 23)
        val post2 = Post(2, 26)
        WallService.add(post1)

        var result = WallService.update(post1)
        assertEquals(true, result)

        result = WallService.update(post2)
        assertEquals(false, result)
    }
}