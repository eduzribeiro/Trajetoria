function a_n2=filtrado_iir_hp(a_n)
%L=800;
%[H0,H1] = butter(4,1/L,'high');          % Highpass Chebyshev Type II
%L=400;
%[H0,H1] = cheby1(6,1,1/L,'high');          % Highpass Chebyshev Type II
L=1000;
[H0,H1] = cheby2(4,60,1/L,'high');          % Highpass Chebyshev Type II
%L=1000;
%[H0,H1] = ellip(4,1,60,1/L,'high');          % Highpass Chebyshev Type II
figure(1);
    G=freqz(H0,H1,64*L);
    F=[0:64*L-1]/(64*L);
    semilogx(F,abs(G),'-o');


    a_n2(:,1)=filter(H0,H1, a_n(:,1));
    a_n2(:,2)=filter(H0,H1, a_n(:,2));
    a_n2(:,3)=filter(H0,H1, a_n(:,3));

end
