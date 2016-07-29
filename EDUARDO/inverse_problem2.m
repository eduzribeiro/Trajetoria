function M=inverse_problem2(gc,gn)
    GN=gn/norm(gn);
    GC=gc/norm(gc);

    x=(GN(1)+GC(1))/2;
    y=(GN(2)+GC(2))/2;
    z=(GN(3)+GC(3))/2;
    
    a=pi;

    M=matrot(x,y,z,a);
end



function M=matrot(x,y,z,a)
    M=zeros(3,3);
    M(1,1)=cos(a)+(x^2)*(1-cos(a));
    M(1,2)=x*y*(1-cos(a))-z*sin(a);
    M(1,3)=x*z*(1-cos(a))+y*sin(a);

    M(2,1)=y*x*(1-cos(a))+z*sin(a);
    M(2,2)=cos(a)+(y^2)*(1-cos(a));
    M(2,3)=y*z*(1-cos(a))-x*sin(a);

    M(3,1)=z*x*(1-cos(a))-y*sin(a);
    M(3,2)=z*y*(1-cos(a))+x*sin(a);
    M(3,3)=cos(a)+(z^2)*(1-cos(a));
end
