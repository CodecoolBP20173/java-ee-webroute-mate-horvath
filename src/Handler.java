import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

public abstract class Handler implements HttpHandler{
    @Override
    public void handle(HttpExchange t) throws IOException {
        String path = t.getRequestURI().getPath();
        String methodType = t.getRequestMethod();
        Method[] methods = this.getClass().getMethods(); //obtain method objects
        for (Method method: methods){
            if (findPath(path, method.getAnnotation(WebRoute.class).path()) && method.getAnnotation(WebRoute.class).method().toString().equals(methodType)){
                try {
                    method.invoke(this, t);
                    break;
                } catch (IllegalAccessException | InvocationTargetException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private boolean findPath(String path, String webRoutePath){
        if (webRoutePath.contains("<")){
            List<String> pathArray = Arrays.asList(path.split("/"));
            List<String> webRouteArray = Arrays.asList(webRoutePath.split("/"));
            pathArray.replaceAll(i->i.replaceAll("/",""));
            webRouteArray.replaceAll(i->i.replaceAll("/",""));
            if (pathArray.size() == webRouteArray.size()){
                for (int i=pathArray.size()-1; i>0; i--){
                    return pathArray.get(i).equals(webRouteArray.get(i)) || webRouteArray.get(i).contains("<");
                }
            }
            return false;
        } else {
            return path.equals(webRoutePath);
        }
    }
}
