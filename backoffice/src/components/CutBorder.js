// borderAt required: [top, right, bottom, left]
function CutBorder({children, borderAt, bg, borderWidth}) {
  bg = bg || "#333"
  borderWidth = borderWidth || '3px';

  const style = { 'background-color': bg, 'border': '0px solid #ffA100' };
  style[`border-${borderAt}-width`] = borderWidth;

  return <div style={style}>
          {children}
        </div>
}
export default CutBorder;
