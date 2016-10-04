function [a_n2]=filtrado_resta(a_n,x,y,z)
   
    a_n2(:,1)=a_n(:,1)-x;
    a_n2(:,2)=a_n(:,2)-y;
    a_n2(:,3)=a_n(:,3)-z;
end
