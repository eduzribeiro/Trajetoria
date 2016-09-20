
classdef Integrador
   properties
        Vx 
        Vy 
        Vz 
        % 
        Sx 
        Sy 
        Sz 
   end
   methods
        function obj =Integrador(initval)
            obj.Vx=initval;
            obj.Vy=initval;
            obj.Vz=initval;

            obj.Sx=initval;
            obj.Sy=initval;
            obj.Sz=initval;
        end
        function obj = integra_rt(obj,dt,a_n2,Db)
    	
            obj.Vx = a_n2(1)*dt;
            obj.Vy = a_n2(2)*dt;
            obj.Vz = a_n2(3)*dt;			
%             obj.Vx = obj.Vx + a_n2(1)*dt;
%             obj.Vy = obj.Vy + a_n2(2)*dt;
%             obj.Vz = obj.Vz + a_n2(3)*dt;


            obj.decide(Db);
					
            obj.Sx = obj.Sx + obj.Vx*dt;
            obj.Sy = obj.Sy + obj.Vy*dt;
            obj.Sz = obj.Sz + obj.Vz*dt;

%             Vx_n = obj.Vx;
%             Vy_n = obj.Vy;
%             Vz_n = obj.Vz;
% 
%             Sx_n = obj.Sx;
%             Sy_n = obj.Sy;
%             Sz_n = obj.Sz;

            obj.Vx
        end

        function decide(obj,Db)

            if (Db == 1)
        
                obj.Vx=0;
                obj.Vy=0;
                obj.Vz=0;
        
            end
    

        end

   end
end