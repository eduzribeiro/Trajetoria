function D = detector_pausa(a_n2,ruido)
N=50;
a_n3=filtrado_iir_hp(a_n2);
a_n4=abs(a_n3);


H=ones(1,N);
H=H/sum(H);

 a_n5(:,1)=filter(H,1, a_n4(:,1));
 a_n5(:,2)=filter(H,1, a_n4(:,2));
 a_n5(:,3)=filter(H,1, a_n4(:,3));
 

end