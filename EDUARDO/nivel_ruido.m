function ruido = nivel_ruido(a,L)

ruido1 = rms(a(1:L,1));
ruido2 = rms(a(1:L,2));
ruido3 = rms(a(1:L,3));

ruido = max([ruido1 ruido2 ruido3]);

   
end