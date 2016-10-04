%
classdef Integrador < handle
   properties
        Vx
        Vy
        Vz
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
        function integra_rt(obj,dt,a_n2,Db)

            obj.Vx = obj.Vx+a_n2(1)*dt;
            obj.Vy = obj.Vy+a_n2(2)*dt;
            obj.Vz = obj.Vz+a_n2(3)*dt;			


            obj.decide(Db);
					
            obj.Sx = obj.Sx + obj.Vx*dt;
            obj.Sy = obj.Sy + obj.Vy*dt;
            obj.Sz = obj.Sz + obj.Vz*dt;
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
