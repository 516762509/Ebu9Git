package weaver.train.webService;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public interface DomeDevPoartlWebService {
    @WebMethod(
            operationName = "getData",
            action = "urn:weaver.train.webService.getData"
    )
    String getDate(String id)throws Exception;
}
