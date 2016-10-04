function a_n2=filtrado_ap(a_n)

    L=40;
    H0=[zeros(1,2*L),1,zeros(1,2*L)];
 
    a_n2(:,1)=filter(H0,1, a_n(:,1));
    a_n2(:,2)=filter(H0,1, a_n(:,2));
    a_n2(:,3)=filter(H0,1, a_n(:,3));
end