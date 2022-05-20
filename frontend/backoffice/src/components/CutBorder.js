// borderAt required: [top, right, bottom, left]
function CutBorder({children, borderAt, bg, borderWidth, ...args}) {
  bg = bg || "#333"
  borderWidth = borderWidth || '1px'

  const style = { 'backgroundColor': bg, 'border': '0px solid #ffA100' }
  style[`border${borderAt}width`] = borderWidth

  return <div style={style} args={args}>
          {children}
        </div>
}
export default CutBorder
