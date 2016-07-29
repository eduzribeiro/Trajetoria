function [Sx_n,Sy_n,Sz_n,Vx_n,Vy_n,Vz_n]=integra_acel_2(dt, a_n2)

    iTotal = size(a_n2,1);
        
    
    Sx_n=zeros(size(a_n2(:,1)));
    Sy_n=zeros(size(a_n2(:,1)));
    Sz_n=zeros(size(a_n2(:,1)));

    Vx_n=zeros(size(a_n2(:,1)));
    Vy_n=zeros(size(a_n2(:,1)));
    Vz_n=zeros(size(a_n2(:,1)));

    
    for u=2:iTotal	
        Vx_n(u) = Vx_n(u-1) + a_n2(u,1)*dt(u);
        Vy_n(u) = Vy_n(u-1) + a_n2(u,2)*dt(u);
        Vz_n(u) = Vz_n(u-1) + a_n2(u,3)*dt(u);
    end

    
    for u=2:iTotal	
        Sx_n(u) = Sx_n(u-1) + Vx_n(u)*dt(u);
        Sy_n(u) = Sy_n(u-1) + Vx_n(u)*dt(u);
        Sz_n(u) = Sz_n(u-1) + Vx_n(u)*dt(u);
    end
    
end
