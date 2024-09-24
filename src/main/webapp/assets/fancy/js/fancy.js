$('.fancybox').fancybox();

$("a.fancyBoxRaf").fancybox({
    prevEffect: 'none',
    nextEffect: 'none',
    closeBtn: true,
    type: 'iframe',
    centerOnScroll: true,
    width: 1200,
    overlayOpacity: 0,
    overlayShow: true
   
});

$("a.fancypdf").fancybox({
    prevEffect: 'none',
    nextEffect: 'none',
    closeBtn: true,
    type: 'iframe',
    centerOnScroll: true,
    width: '100%',
    height: '100%',
    overlayOpacity: 0,
    overlayShow: true,
    
    fitToView: false,
    iframe : {
      preload : false
    }
});

