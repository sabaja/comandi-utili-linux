
@Service
public class RecuperaElencoFacade implements RecuperaElencoFidiService {

    @Autowired
    private ElencoFidiConnector connector;

    @Autowired
    private ElencoFidiRequestTransformer elencoFidiRequestTransformer;

    @Autowired
    private ElencoFidiResponseTransformer elencoFidiResponseTransformer;

    @Override
    public List<ElencoFidiExternalOutputBin> recuperaElencoFidi(ElencoFidiExternalInputBin input) {
        return connector.call(input, elencoFidiRequestTransformer, elencoFidiResponseTransformer);
    }
}
