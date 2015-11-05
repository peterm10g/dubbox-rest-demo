package top.crowley.dubbox.rest.demo.api.pojo;

import java.util.List;

public class QueryRespMessage<T> {

    private RespMessage respMessage;
    private List<T> list;
    
    public RespMessage getRespMessage() {
        return respMessage;
    }
    public void setRespMessage(RespMessage respMessage) {
        this.respMessage = respMessage;
    }
    public List<T> getList() {
        return list;
    }
    public void setList(List<T> list) {
        this.list = list;
    }
}
