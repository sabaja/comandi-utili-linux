import com.intesasanpaolo.bear.lmbe0.documento.mapper.GetDocumentiCheckListMapper;
import com.intesasanpaolo.bear.lmbe0.documento.model.bin.GetDocumentiChecklistOutputBin;
import com.intesasanpaolo.bear.lmbe0.documento.resource.DocumentoListResource;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.junit.Assert;
import java.util.ArrayList;
import java.util.List;
@RunWith(MockitoJUnitRunner.class)
public class GetDocumentiChecklistAssemblerTest{
    private GetDocumentiChecklistAssembler getDocumentiChecklistAssembler;
    @Mock
    private GetDocumentiCheckListMapper mapper;
    @Before
    public void before(){getDocumentiChecklistAssembler = new GetDocumentiChecklistAssembler();}
    @Test
    public void instantiaResourceTeste(){
        List<GetDocumentiChecklistOutputBin> output = new ArrayList<>();
        List<DocumentoListResource> resources;
        resources = getDocumentiChecklistAssembler.toResources(output);
        Assert.assertNotNull(output);
        Assert.assertNotNull(resources);
    }
}
