package com.csonezp.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ParamPrint {
    /**
     * 仅仅打印request内的参数，否则打印函数所有入参
     */
    boolean requestOnly() default false;

    /**
     * 排除参数序列中不用打日志（1.减少不必要的日志打印；2避免使用fastjson引起的环岛bug[特别是HttpServletRequest 和 HttpServletResponse]）
     * 打日志的index序列 0，1，2 …………
     * eg functionA(int a, int b ,Object c,String d )
     * 如果你要只要打印c,那么
     * LogPrintAnnotation(exceptParamIndex ={0,2,3})
     * 默认不排除，那么打印所有的参数序列
     *
     * @return
     */
    String[] exceptParamIndex() default {"token"};
}
