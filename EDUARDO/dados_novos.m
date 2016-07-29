function h = dados_novos(Dadosname,output,amostras,offset)
  
    h=0;

    Dados = load(Dadosname);

    Dados_n = Dados([offset:amostras:end-offset+1],:);

    save(output,'Dados_n','-ascii')
    
end
