function a_n2=filtrado_hp(a_n)

    L=100;
    H0=fir1(4*L,1/L,'high');
 
    a_n2(:,1)=filter(H0,1, a_n(:,1));
    a_n2(:,2)=filter(H0,1, a_n(:,2));
    a_n2(:,3)=filter(H0,1, a_n(:,3));
end