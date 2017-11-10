package ut.com.expdatacloud.ty;

import org.junit.Test;
import com.expdatacloud.ty.api.MyPluginComponent;
import com.expdatacloud.ty.impl.MyPluginComponentImpl;

import static org.junit.Assert.assertEquals;

public class MyComponentUnitTest
{
    @Test
    public void testMyName()
    {
        MyPluginComponent component = new MyPluginComponentImpl(null);
        assertEquals("names do not match!", "myComponent",component.getName());
    }
}