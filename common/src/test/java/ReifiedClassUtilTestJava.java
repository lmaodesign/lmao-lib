import design.lmao.lib.common.ClassReifiedParameterUtil;
import org.junit.jupiter.api.Test;

public class ReifiedClassUtilTestJava {

    @Test
    public void test() {
        ClassReifiedParameterUtil.hasTypeOf(Example.class, Lol2.class);
    }
}

abstract class Example2<T> {

}

class Lol2 extends Example2<String> {

}
