package com.chisapp.common.utils.fileutils;

import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;


/**
 * @Author: Tandy
 * @Date: 2020-06-18 13:23
 * @Version 1.0
 */
@Aspect
@Component
public class FileUtilsAspect {

    /**
     * 公共切入点
     * execution (* com.sample.service.impl..*.*(..))
     *  1、execution(): 表达式主体。
     *  2、第一个*号：表示返回类型，*号表示所有的类型。
     *  3、包名：表示需要拦截的包名，后面的两个句点表示当前包和当前包的所有子包，com.sample.service.impl包、子孙包下所有类的方法。
     *  4、第二个*号：表示类名，*号表示所有的类。
     *  5、*(..):最后这个星号表示方法名，*号表示所有的方法，后面括弧里面表示方法的参数，两个句点表示任何参数。
     */
    /*
    @Pointcut("execution(* com.chisapp.common.utils.FileUtils.*(..))")
    public void currentPointcut () {}
    */

    /**
     * 使用公共切入点执行的 @after
     */
    /*
    @After("currentPointcut()")
    public void doAfter () {

    }
    */
    /*
    @After("execution(public * com.chisapp.common.utils.fileutils.*.*(..))")
    public void doAfter () {
        System.out.println("保存文件后开始执行的内容...............................");
    }
    */






}
