package com.flouis.npjt.aspect;

import com.flouis.npjt.utils.StringTool;
import com.google.common.collect.Lists;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Flouis
 * @date 2018/09/22
 * @description Aspect class of SqlUtil
 **/

@Component
@Aspect
public class NpjtSqlAspect {

    private static final Logger logger = LoggerFactory.getLogger(NpjtSqlAspect.class);

    @Pointcut("execution(* com.flouis.npjt.utils.SqlUtil.*(..))")
    public void pointcut(){}

    @Before(value = "pointcut()")
    public void showSql(JoinPoint joinPoint){
        // get sql that will be execute
        String sql = StringTool.toString( Lists.newArrayList(joinPoint.getArgs()).get(0) );
        logger.info("Executing SQL: " + sql);
    }

}
