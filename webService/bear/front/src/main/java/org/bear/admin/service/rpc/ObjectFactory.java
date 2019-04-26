
package org.bear.admin.service.rpc;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the org.bear.admin.service.rpc package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _QueryItemById_QNAME = new QName("http://rpc.service.admin.bear.org/", "queryItemById");
    private final static QName _QueryItemByIdResponse_QNAME = new QName("http://rpc.service.admin.bear.org/", "queryItemByIdResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: org.bear.admin.service.rpc
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link QueryItemById }
     * 
     */
    public QueryItemById createQueryItemById() {
        return new QueryItemById();
    }

    /**
     * Create an instance of {@link QueryItemByIdResponse }
     * 
     */
    public QueryItemByIdResponse createQueryItemByIdResponse() {
        return new QueryItemByIdResponse();
    }

    /**
     * Create an instance of {@link Item }
     * 
     */
    public Item createItem() {
        return new Item();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryItemById }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://rpc.service.admin.bear.org/", name = "queryItemById")
    public JAXBElement<QueryItemById> createQueryItemById(QueryItemById value) {
        return new JAXBElement<QueryItemById>(_QueryItemById_QNAME, QueryItemById.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link QueryItemByIdResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://rpc.service.admin.bear.org/", name = "queryItemByIdResponse")
    public JAXBElement<QueryItemByIdResponse> createQueryItemByIdResponse(QueryItemByIdResponse value) {
        return new JAXBElement<QueryItemByIdResponse>(_QueryItemByIdResponse_QNAME, QueryItemByIdResponse.class, null, value);
    }

}
