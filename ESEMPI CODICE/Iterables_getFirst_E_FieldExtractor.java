   private GestoreDiRiferimentoBin prepareInput(List<SoggettoPraticaResource> listaSoggetti) {
        SoggettoPraticaResource soggettoPraticaResource = Iterables.getFirst(listaSoggetti, null);
        GestoreDiRiferimentoBin bin = new GestoreDiRiferimentoBin();
        bin.setSnsg(FieldExtractor.extract(soggettoPraticaResource, "soggetto", "codSnsg"));
        bin.setCodAbi(FieldExtractor.extract(soggettoPraticaResource, "soggetto", "codAbi"));
        return bin;
    }
	
	