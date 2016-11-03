% Classe que calcula a media dos dados quando uma nova pausa � detectada
classdef detectordepausa_rt < handle
   properties
       
       fator
       U
       Tau
       Db
       Dsp
       Dsa
       Dx
       Dy
       Dz
       
       
       %Filtro s� pausa
       F
       FM
      
       %Filtro s� ativo
       FF
       
   end
   methods
       %Construtor inializador do objeto  
        function obj = detectordepausa_rt
        
            obj.fator = 2.5;
            obj.U = 0.3;
            obj.Tau = 0.05; %Valores mais baixos, decaimento mais lento
            obj.Db = 0;
            obj.Dsp = 0;
            obj.Dsa = 0;
            obj.Dx = 0;
            obj.Dy = 0;
            obj.Dz = 0;
            
            
            P=13;
            H=fir1(4*P,1/P,'high');
            G=1;
            %[H,G] = cheby2(4,60,1/1000,'high');
            obj.F=FiltroIIR(H,G);
            
            HM=ones(1,50);
            HM=HM/sum(HM);
            obj.FM=FiltroIIR(HM,1);
            
            P = 20;
            H1 = fir1(4*P,1/P,'low');
            obj.FF=FiltroIIR(H1,1);
            
        end
        
        %an: input signal  [3x1]
        %nivel_ruido: calculado nas outras fun��es [1x1]
        %return :::
        %Db: Pausa detectada [1] ou Pausa n�o detectada [0] 
        
        function Db = detector_pausa(obj,an,nivel_ruido)
            
          obj.Dsp = obj.detector_so_pausa(an,nivel_ruido);
          
          obj.Dsa = obj.detector_so_ativo(an,nivel_ruido);
          
          if obj.Dsp == 1 && obj.Dsa == 0
          
              obj.Db = 1;
            
          else
              
              obj.Db = 0;
          
          end
          
          Db = obj.Db;
            
         end
        
        
         function Dsp = detector_so_pausa(obj,a_n2,ruido)
             
             a_n3 = obj.F.filter_rt(a_n2);
             a_n4 = abs(a_n3);
             a_n5 = obj.FM.filter_rt(a_n4);
             
             
             D = a_n5<=obj.fator*ruido;
             
             
             if sum(D)==3
                
                 obj.Dsp = 1;
                 
             else
                 
                 obj.Dsp = 0;
                 
             end
             
                Dsp = obj.Dsp; 
            
         end
          
         function Dsa = detector_so_ativo(obj,a_n2,ruido)
             
             
             a_x = a_n2 - obj.FF.filter_rt(a_n2);
             a_x2 = abs(a_x);
             
             D = a_x2>=obj.fator*ruido;
             
             dx = D(1);
             dy = D(2);
             dz = D(3);
            
             if (dx==0)
                
                 dx = obj.Dx*exp(-obj.Tau);
             
             end
             
             obj.Dx = dx;

            if (dy==0)
        
                dy=obj.Dy*exp(-obj.Tau);
    
            end
            
            obj.Dy = dy;

            if (dz==0)
        
                dz=obj.Dz*exp(-obj.Tau);
    
            end   
          
            obj.Dz = dz;

            if ((obj.Dx>obj.U) || (obj.Dy>obj.U) || (obj.Dz>obj.U))
         
                obj.Dsa = 1;
         
            else
         
                obj.Dsa = 0;
         
            end
            
                Dsa = obj.Dsa;
            
         end

   end
end
