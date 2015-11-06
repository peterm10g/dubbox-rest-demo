package top.crowley.dubbox.rest.demo.api.util;

import java.io.IOException;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerResponseContext;
import javax.ws.rs.container.ContainerResponseFilter;

public class CacheControlFilter implements ContainerResponseFilter{

    @Override
    public void filter(ContainerRequestContext req, ContainerResponseContext res)
            throws IOException {
        if (req.getMethod().equals("GET")) {
            res.getHeaders().add("Cache-Control", "someValue");
            System.out.println(res.getHeaders());
        }
    }

}
