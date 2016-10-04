function [Dsp Dsa Db] = detector_pausa(a_n2,ruido)

% Retorna 1 quando detecta pausa

    Dsp=detector_so_pausa(a_n2,ruido,1.5);
    
    Dsa = detector_so_ativo(a_n2,ruido,1.5);
    
    Db = zeros(size(Dsp));
    
    for II=1:length(Dsp) 

        if ((Dsa(II)==0) && (Dsp(II)==1))
         
            Db(II) = 1;
         
        else
         
            Db(II) = 0;
         
        end
     
    end

 

end

function D=detector_so_pausa(a_n2,ruido,factor)

N=50;

a_n3=filtrado_iir_hp(a_n2);
a_n4=abs(a_n3);


H=ones(1,N);
H=H/sum(H);

 a_n5(:,1)=filter(H,1, a_n4(:,1));
 a_n5(:,2)=filter(H,1, a_n4(:,2));
 a_n5(:,3)=filter(H,1, a_n4(:,3));
 
 D1 = a_n5(:,1)<=factor*ruido;
 D2 = a_n5(:,2)<=factor*ruido;
 D3 = a_n5(:,3)<=factor*ruido;
 
 D = zeros(size(D1));
 
 for II=1:length(D1) 

     if ((D1(II)==1) && (D2(II)==1) && (D3(II)==1))
         
         D(II) = 1;
         
     else
         
         D(II) = 0;
         
     end
     
 end

end

function D = detector_so_ativo(a_n2,ruido,factor)
P=40;
H0 = fir1(4*P,1/P,'low');

a_n(:,1)=filter(H0,1, a_n2(:,1));
a_n(:,2)=filter(H0,1, a_n2(:,2));
a_n(:,3)=filter(H0,1, a_n2(:,3));

a_x(:,1)=a_n2(:,1)-a_n(:,1);
a_x(:,2)=a_n2(:,2)-a_n(:,2);
a_x(:,3)=a_n2(:,3)-a_n(:,3);

a_x2=abs(a_x);



D1 = a_x2(:,1)>=factor*ruido;
D2 = a_x2(:,2)>=factor*ruido;
D3 = a_x2(:,3)>=factor*ruido;


Dx = zeros(size(a_x2(:,1)));
Dy = zeros(size(a_x2(:,1)));
Dz = zeros(size(a_x2(:,1)));
Dx(1) = D1(1);
Dy(1) = D2(1);
Dz(1) = D3(1);
Tau=0.1;
for II=2:length(Dx) 

    Dx(II)=D1(II);
    Dy(II)=D2(II);
    Dz(II)=D3(II);
    
    if (Dx(II)==0)
        Dx(II)=Dx(II-1)*exp(-Tau);
    end

    if (Dy(II)==0)
        Dy(II)=Dy(II-1)*exp(-Tau);
    end

    if (Dz(II)==0)
        Dz(II)=Dz(II-1)*exp(-Tau);
    end   
      
     
 end


D = zeros(size(D1));
U = 0.3;
 
 for II=1:length(D1) 

     if ((Dx(II)>U) || (Dy(II)>U) || (Dz(II)>U))
         
         D(II) = 1;
         
     else
         
         D(II) = 0;
         
     end
     
 end
 

end