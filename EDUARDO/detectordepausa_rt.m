% Classe que calcula a media dos dados quando uma nova pausa é detectada
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
       Dmx
       Dmy
       Dmz
       Dma
       
       
       %Filtro só pausa
       F
       FM
      
       %Filtro só ativo
       FF
       
       %Filtro alinhamento
       FA
       FMA
       FMA2
       
   end
   methods
       %Construtor inializador do objeto  
        function obj = detectordepausa_rt
        
            obj.fator = 3;
            obj.U = 0.3;
            obj.Tau = 0.05; %Valores mais baixos, decaimento mais lento
            obj.Db = 0;
            obj.Dsp = 0;
            obj.Dsa = 0;
            obj.Dx = 0;
            obj.Dy = 0;
            obj.Dz = 0;
            obj.Dmx = 0;
            obj.Dmy = 0;
            obj.Dmz = 0;
            obj.Dma = 0;
            
            
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
            
            
            P=7;
            H2=fir1(4*P,1/P,'low');
            G2=1;
            obj.FA=FiltroIIR(H2,G2);
            
            HMA=ones(1,30);
            HMA=HMA/sum(HMA);
            obj.FMA=FiltroIIR(HMA,1);
            
            HMA2=ones(1,10);
            HMA2=HMA2/sum(HMA2);
            obj.FMA2=FiltroIIR(HMA2,1);
            
            
        end
        
        %an: input signal  [3x1]
        %nivel_ruido: calculado nas outras funções [1x1]
        %return :::
        %Db: Pausa detectada [1] ou Pausa não detectada [0] 
        
        function [Db,Dma] = detector_pausa(obj,an,nivel_ruido)
            
          obj.Dsp = obj.detector_so_pausa(an,nivel_ruido);
          
          obj.Dsa = obj.detector_so_ativo(an,nivel_ruido);
          
          obj.Dma = obj.detector_alinhamento(an);
          
          if obj.Dsp == 1 && obj.Dsa == 0
          
              obj.Db = 1;
            
          else
              
              obj.Db = 0;
          
          end
          
          Db = obj.Db;
          Dma = obj.Dma;
          
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

   
   

   function Dma = detector_alinhamento(obj,a_n2)
             
             a_dc = obj.FA.filter_rt(a_n2);
             a_x = a_n2 - a_dc;
             a_x2 = abs(a_x);
             AC = obj.FMA.filter_rt(a_x2);
             
             DC = obj.FMA2.filter_rt(a_dc);
             
             DC = abs(DC);
             %AC = abs(AC);
             
             B = DC./AC;             
             
             DM = B>1;
             
             dmx = DM(1);
             dmy = DM(2);
             dmz = DM(3);
            
             if (dmx==0)
                
                 dmx = obj.Dmx*exp(-obj.Tau);
             
             end
             
             obj.Dmx = dmx;

            if (dmy==0)
        
                dmy=obj.Dmy*exp(-obj.Tau);
    
            end
            
            obj.Dmy = dmy;

            if (dmz==0)
        
                dmz=obj.Dmz*exp(-obj.Tau);
    
            end   
          
            obj.Dmz = dmz;

            if ((obj.Dmx>obj.U) || (obj.Dmy>obj.U) || (obj.Dmz>obj.U))
         
                obj.Dma = 1;
         
            else
         
                obj.Dma = 0;
         
            end
            
            Dma = obj.Dma;
            Dma=B(2);
            
         end
         

   
end
   
end
