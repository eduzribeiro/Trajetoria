function a_n2=filtrado_lp(a_n)

    %notch filter 0 freq
    L=40;
    H0=fir1(L,1/4);
 
    a_n2(:,1)=filter(H0,1, [mean(a_n(1:20,1))*ones(L,1);a_n(:,1)]);
    a_n2(:,2)=filter(H0,1, [mean(a_n(1:20,2))*ones(L,1);a_n(:,2)]);
    a_n2(:,3)=filter(H0,1, [mean(a_n(1:20,3))*ones(L,1);a_n(:,3)]);
    a_n2=a_n2(L+1:end,:);
end
