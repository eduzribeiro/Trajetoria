function [x y z]=filtrado_dados_resta(a_n,inicio,L)

    x=mean(a_n(inicio:inicio+L-1,1));
    y=mean(a_n(inicio:inicio+L-1,2));
    z=mean(a_n(inicio:inicio+L-1,3));
    
%     dx = std(a_n(1:L,1));
%     dy = std(a_n(1:L,2));
%     dz = std(a_n(1:L,3));
%     
%     m = [dx dy dz]
        
end
