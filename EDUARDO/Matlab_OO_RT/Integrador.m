% Classe que integra as acelerações
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
       %Construtor inializador do objeto
       % inival: inicia todas as variaveis
        function obj =Integrador(initval)
            obj.Vx=initval;
            obj.Vy=initval;
            obj.Vz=initval;

            obj.Sx=initval;
            obj.Sy=initval;
            obj.Sz=initval;
        end
        
        %a_n2: input signal  [1x3] ou [3x1]
        %dt: input time interval [1x1]
        %Db: input saída Detector de pausa 0 [movimento] ou 1 [pausa]
        %return :::
        %Sx: Deslocamento no eixo x [1x1]
        %Sy: Deslocamento no eixo y [1x1]
        %Sz: Deslocamento no eixo z [1x1]
        
        function [Sx,Sy,Sz]=integra_rt(obj,dt,a_n2,Db)

            obj.Vx = obj.Vx+a_n2(1)*dt;
            obj.Vy = obj.Vy+a_n2(2)*dt;
            obj.Vz = obj.Vz+a_n2(3)*dt;			


            obj.decide(Db);
					
            obj.Sx = obj.Sx + obj.Vx*dt;
            obj.Sy = obj.Sy + obj.Vy*dt;
            obj.Sz = obj.Sz + obj.Vz*dt;
            
            Sx = obj.Sx;
            Sy = obj.Sy;
            Sz = obj.Sz;
            
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
