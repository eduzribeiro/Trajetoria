function [Sx_n,Sy_n,Sz_n,Vx_n,Vy_n,Vz_n]=integra_acel(dt,bias,a_n2,pausa)

    a_n2 = a_n2(1:end,:);


    iTotal = size(a_n2,1);

    % Cálculo velocidade e deslocamento

    Vx = 0;
    Vy = 0;
    Vz = 0;
    % 
    Sx = 0;
    Sy = 0;
    Sz = 0;
    %         


    for u=1:iTotal
    	
			
        Vx = Vx + a_n2(u,1)*dt(u);
        Vy = Vy + a_n2(u,2)*dt(u);
        Vz = Vz + a_n2(u,3)*dt(u);


        [Vx Vy Vz] = decide(Vx,Vy,Vz,bias,pausa(u),u);
					
        Sx = Sx + Vx*dt(u);
        Sy = Sy + Vy*dt(u);
        Sz = Sz + Vz*dt(u);

        Vx_n(u) = Vx;
        Vy_n(u) = Vy;
        Vz_n(u) = Vz;

        Sx_n(u) = Sx;
        Sy_n(u) = Sy;
        Sz_n(u) = Sz;

    end


    
end

function [Vx2 Vy2 Vz2] = decide(Vx,Vy,Vz,bias,p,u)

Vx2 = Vx;
Vy2 = Vy;
Vz2 = Vz;

    if (bias > u) || (p == 1)
        
        Vx2=0;
        Vy2=0;
        Vz2=0;
        
    end
    

end

