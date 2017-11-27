package server;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Jay
 */
public class ChannelControllerTest {
    
    ChannelController instance;
    
    @Before
    public void setUp() {
        instance = ChannelController.getInstance();
    }

    /**
     * Test of getInstance method, of class ChannelController.
     */
    @Test
    public void testGetInstance() {
        System.out.println("getInstance test ---");
        ChannelController expResult = ChannelController.getInstance();
        ChannelController result = ChannelController.getInstance();
        assertTrue(expResult == result);
    }

    /**
     * Test of parse method, of class ChannelController.
     */
    @Test
    public void testGetUserByNickname() {
        System.out.println("getUser test ---");
        String nickname = "test_name";
        User expected = new User(null, nickname);
        instance.users.add(expected);
        User result = instance.getUserByNickname(nickname);
        assertTrue(result == expected);
        assertTrue(null == instance.getUserByNickname(nickname+nickname));
    }
    
    /**
     * Test of isValidChannelName method, of class ChannelController.
     */
    @Test
    public void testIsInvalidChannelName() {
        System.out.println("isInvalidChannelName test ---");
        String validName = "Valid Name";
        String invalidName1 = "Invalid_Name";
        String invalidName2 = "";
        String invalidName3 = " ";
        String invalidName4 = "troll<script>document.cookie='lol'</script>";
        assertFalse(instance.isInvalidChannelName(validName));
        assertTrue(instance.isInvalidChannelName(invalidName1));
        assertTrue(instance.isInvalidChannelName(invalidName2));
        assertTrue(instance.isInvalidChannelName(invalidName3));
        assertTrue(instance.isInvalidChannelName(invalidName4));
    }
    
}
