
@Component
@Scope(value = ConfigurableBeanFactory.SCOPE_SINGLETON)
public class FidoGaranziaResourceAssembler extends BaseResourceAssemblerSupport<FidoGaranzia, FidoGaranziaResource> {
    @Autowired
	private FidoGaranziaMapper mapper;
    
	public FidoGaranziaResourceAssembler() {
        super(FidoGaranzia.class, FidoGaranziaResource.class);
	}
	
    @Override
	protected FidoGaranziaResource instantiateResource(FidoGaranzia model) {
        return mapper.mapToResource(model);
	}
	
    @Override
	public FidoGaranziaResource toResource(FidoGaranzia model) {
        String id = String.valueOf(model.getId());
		String resourcePath = buildResourcePath(id, "pratiche/" + model.getIdPratica() + "/fidogaranzia");
        return createResourceWithId(resourcePath, model);
	}
}


