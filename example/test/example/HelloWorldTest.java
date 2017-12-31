/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package example;

import com.sun.jdi.AbsentInformationException;
import com.sun.jdi.IncompatibleThreadStateException;
import com.sun.jdi.LocalVariable;
import com.sun.jdi.StackFrame;
import com.sun.jdi.ThreadReference;
import com.sun.jdi.Value;
import com.sun.jdi.event.BreakpointEvent;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdiscript.JDIScript;
import org.jdiscript.handlers.OnBreakpoint;
import org.jdiscript.util.VMLauncher;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;



/**
 *
 * @author bonnie
 */
public class HelloWorldTest {
    
    public HelloWorldTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of main method, of class HelloWorld.
     */
    @Test
    public void testMain() {
        String OPTIONS = "-cp ./build/classes/";
        String MAIN = "example.HelloWorld";
        String CLASSREF = "example.HelloWorld";
        String METHOD = "main";

        JDIScript j = new JDIScript(new VMLauncher(OPTIONS, MAIN).start());
        

        j.onMethodExit(CLASSREF, METHOD, new OnBreakpoint() {
            @Override
            public void breakpoint(BreakpointEvent e) {
                e.request().disable();
                ThreadReference thread = e.thread();
                
                try {
                    StackFrame stackFrame = thread.frame(0);
                    Map<LocalVariable,Value> visibleVariables = (Map<LocalVariable,Value>) stackFrame.getValues(stackFrame.visibleVariables());
                    for(Map.Entry<LocalVariable, Value> entry : visibleVariables.entrySet()){
                        System.out.println(entry.getKey() + ":" + entry.getValue());
                    }
                } catch (IncompatibleThreadStateException | AbsentInformationException ex) {
                    Logger.getLogger(HelloWorldTest.class.getName()).log(Level.SEVERE, null, ex);
                }
                
            }
        });
        j.run();

    }
    
}
