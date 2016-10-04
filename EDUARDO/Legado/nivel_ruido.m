function ruido = nivel_ruido(a,L)

ruido1 = max(abs(a(1:L,1)));
ruido2 = max(abs(a(1:L,2)));
ruido3 = max(abs(a(1:L,3)));

ruido = max([ruido1 ruido2 ruido3]);

   
end