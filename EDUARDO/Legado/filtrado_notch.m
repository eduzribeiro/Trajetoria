function a_n2=filtrado_notch(a_n)

    %notch filter 0 freq
    r=0.97;
    H0=[1 -2 1];
    H1=[1 -2*r r^2];
    figure(1);
    G=freqz(H0,H1,200);
    F=[0:200-1]/200;
    semilogx(F,G);


    L=4;
%     a_n2(:,1)=filter(H0,H1, [mean(a_n(1:20,1))*ones(L,1);a_n(:,1)]);
%     a_n2(:,2)=filter(H0,H1, [mean(a_n(1:20,2))*ones(L,1);a_n(:,2)]);
%     a_n2(:,3)=filter(H0,H1, [mean(a_n(1:20,3))*ones(L,1);a_n(:,3)]);
%     a_n2=a_n2(L+1:end,:);

    a_n2(:,1)=filter(H0,H1, a_n(:,1));
    a_n2(:,2)=filter(H0,H1, a_n(:,2));
    a_n2(:,3)=filter(H0,H1, a_n(:,3));

end
