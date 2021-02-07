import com.wh.testapi.wework.Wework;
import com.wh.testapi.wework.WeworkConfig;
import org.junit.jupiter.api.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.not;

//测试呢
public class TestGetToken {
    @Test
    void testToken(){
        Wework wework=new Wework();
        String token=wework.getWeworkToken(WeworkConfig.getInstance().secret);
        assertThat(token,not(equalTo(null)));

    }
}
