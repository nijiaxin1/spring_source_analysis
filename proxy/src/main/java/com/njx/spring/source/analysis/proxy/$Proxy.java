package com.njx.spring.source.analysis.proxy;
import com.njx.spring.source.analysis.dao.Dao;
public class $Proxy implements Dao{
private Dao target;
 public $Proxy ( Dao target){
this.target=target;
}
 public void query ( Integer Integer0  ){ 
System.out.println("log"); 
target.query( Integer0);
}
}
