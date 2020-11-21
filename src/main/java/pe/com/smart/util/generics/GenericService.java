package pe.com.smart.util.generics;

import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public abstract class GenericService {

    @Autowired
    protected HttpServletRequest request;

}
